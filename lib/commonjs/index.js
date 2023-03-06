"use strict";

Object.defineProperty(exports, "__esModule", {
  value: true
});
exports.default = void 0;
var _reactNative = require("react-native");
/**
 * @format
 */

let loader;
if (_reactNative.Platform.OS === 'android') {
  const {
    BundleManagerModule
  } = _reactNative.NativeModules;
  loader = BundleManagerModule;
} else {
  const {
    BundleManager
  } = _reactNative.NativeModules;
  loader = BundleManager;
}
console.log('BundleManagerModule', loader);
var _default = loader;
exports.default = _default;
//# sourceMappingURL=index.js.map