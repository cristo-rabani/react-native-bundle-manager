/**
 * @format
 */

import { NativeModules, Platform } from 'react-native';
let loader;
if (Platform.OS === 'android') {
  const {
    BundleManagerModule
  } = NativeModules;
  loader = BundleManagerModule;
} else {
  const {
    BundleManager
  } = NativeModules;
  loader = BundleManager;
}
console.log('BundleManagerModule', loader);
export default loader;
//# sourceMappingURL=index.js.map