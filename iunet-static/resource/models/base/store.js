'use strict';

export let store = function (durables) {
  this.durables = durables == true ? durables : false;
};

store.prototype.support = function (_durables) {
  try {
    if (this.durables || _durables) {
      return 'localStorage' in window && window['localStorage'] !== null;
    } else {
      return 'sessionStorage' in window && window['sessionStorage'] !== null;
    }
  } catch (e) {
    return false;
  }
};
store.prototype.add = function (key, value, _durables) {
  if (this.support()) {
    if (this.durables || _durables) {
      localStorage.setItem(key, value);
    } else {
      sessionStorage.setItem(key, value);
    }
  }
};
store.prototype.get = function (key, _durables) {
  if (this.support()) {
    if (this.durables || _durables) {
      return localStorage.getItem(key)
    } else {
      return sessionStorage.getItem(key)
    }
  }
};
store.prototype.remove = function (key, _durables) {
  if (this.support()) {
    if (this.durables || _durables) {
      return localStorage.removeItem(key);
    } else {
      return sessionStorage.removeItem(key);
    }
  }
};
store.prototype.clear = function (_durables) {
  if (this.support()) {
    if (this.durables || _durables) {
      return localStorage.clear();
    } else {
      return sessionStorage.clear();
    }
  }
};
