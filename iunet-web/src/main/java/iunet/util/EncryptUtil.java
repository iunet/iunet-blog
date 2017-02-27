package iunet.util;
import java.security.MessageDigest;
import java.util.Random;


public class EncryptUtil {
			
	private static String[] hexDigits={"0","1","2","3","4","5","6","7","8","9","A","B","C","D","E","F"};
	private static int saltLength = 4;
	
	/**加密不可逆
	 * @param buf
	 * @return
	 * @throws Exception
	 */
	public static String md5Encrypt(String buf) throws Exception
	{
		try{
			MessageDigest instance = MessageDigest.getInstance("MD5");
			instance.update(buf.getBytes());
			byte[] data = instance.digest();		
			return byteArraytoHexString(data);			
		}
		catch(Exception e)
		{
			throw new Exception("md5Encrypt fail" + e.getMessage());
		}
	}
	
	/**加密不可逆，不重复
	 * @param buf
	 * @return
	 * @throws Exception
	 */
	public static String shaEncrypt(String buf) throws Exception
	{
		if (buf == null) buf = "";
		buf = StringUtil.padLeft(buf,'0',6);
		try{
			MessageDigest instance = MessageDigest.getInstance("SHA");
			instance.update(buf.getBytes());	        
	        byte[] byteTemp = instance.digest();	        	       
			
			return 	Base64String.toBase64String(createShaEncryptByte(byteTemp),false);
		}
		catch(Exception e)
		{
			throw new Exception("shaEncrypt fail" + e.getMessage());
		}
	}
	
	/**加密比对
	 * @param str
	 * @param encryptStr
	 * @return
	 */
	public static boolean shaCompare(String str,String encryptStr)
	{
		if (str == null) str = "";
        str = str.length() < 6 ? StringUtil.padLeft(str, '0',6) : str;

        try
        {
	        MessageDigest instance = MessageDigest.getInstance("SHA");
	        instance.update(str.getBytes());	        
	        byte[] byteNewEncryption = instance.digest();		        
	        byte[] byteOldEncryption = Base64String.fromBase64String(encryptStr);
	
	        if (compareEncrypts(byteOldEncryption, byteNewEncryption) == true)
	            return true;
	        else
	            return false;
        }
        catch(Exception ex)
        {
        	return false;
        }
	}

	private static String byteToHexString(byte b)
	{
		int n = b;
		if ( n < 0 )
		{
			n = 256 + n;
		}
		
		int d1 = n / 16;
		int d2 = n % 16;
		
		return hexDigits[d1] + hexDigits[d2];
	}
	
	private static String byteArraytoHexString(byte[] b)
	{
		String res="";
		for( int i=0; i<b.length; ++i )
		{
			res += byteToHexString(b[i]);
		}
		
		return res;
	}
		
	private static byte[] createShaEncryptByte(byte[] unsaltedByte) throws Exception
    {
        //Create a salt value
        byte[] saltValue = new byte[saltLength];
        new Random().nextBytes(saltValue);
      
        return createSaltedEncrytByte(saltValue, unsaltedByte);
    }
    // create a salted password given the salt value
    private static byte[] createSaltedEncrytByte(byte[] saltValue, byte[] unsaltedByte) throws Exception
    {
        // add the salt to the hash
        byte[] rawSalted = new byte[unsaltedByte.length + saltValue.length];
        for(int i=0;i<unsaltedByte.length;i++)
        	rawSalted[i] = unsaltedByte[i];
        for(int i=0;i<saltValue.length;i++)
        	rawSalted[unsaltedByte.length+i] = saltValue[i];
        
        MessageDigest instance = MessageDigest.getInstance("SHA");
        instance.update(rawSalted);	
        byte[] saltedByte = instance.digest();	

        // add the salt value to the salted hash
        byte[] encrytByte = new byte[saltedByte.length + saltValue.length];
        for(int i=0;i<saltedByte.length;i++)
        	encrytByte[i] = saltedByte[i];
        for(int i=0;i<saltValue.length;i++)
        	encrytByte[saltedByte.length+i] = saltValue[i];
               
        return encrytByte;
    }

    private static boolean compareEncrypts(byte[] storedEncryptByte, byte[] hashedEncryptByte) throws Exception
    {
        if (storedEncryptByte == null || hashedEncryptByte == null || 
        		hashedEncryptByte.length != storedEncryptByte.length - saltLength)
            return false;

        // get the saved saltValue
        byte[] saltValue = new byte[saltLength];
        int saltOffset = storedEncryptByte.length - saltLength;
        for (int i = 0; i < saltLength; i++)
            saltValue[i] = storedEncryptByte[saltOffset + i];//4

        byte[] saltedNewEncryptByte = createSaltedEncrytByte(saltValue, hashedEncryptByte);

        // compare the values
        return compareByteArray(storedEncryptByte, saltedNewEncryptByte);
    }

    private static boolean compareByteArray(byte[] array1, byte[] array2)
    {
        if (array1.length != array2.length)
            return false;
        for (int i = 0; i < array1.length; i++)
        {
            if (array1[i] != array2[i])
                return false;
        }
        return true;
    }
}