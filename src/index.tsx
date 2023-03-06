/**
 * @format
 */

import { NativeModules, Platform } from 'react-native';

export type RunningMode = 'LOCAL' | 'REMOTE';

type BundleManagerType = {
  setPackagerHost(host: string): Promise<void>;
  load(url: string): Promise<void>;
  runningMode(): Promise<RunningMode>;
};

let loader;


if (Platform.OS === 'android') {
  const { BundleManagerModule } = NativeModules;
  loader = BundleManagerModule;
} else {
  const { BundleManager } = NativeModules;
  loader = BundleManager;
}
console.log('BundleManagerModule', loader);

export default loader as BundleManagerType;
