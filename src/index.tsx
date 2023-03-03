/** @format */

import { NativeModules } from 'react-native';

export type RunningMode = 'LOCAL' | 'REMOTE';

type BundleManagerType = {
  setPackagerHost(host: string): Promise<void>;
  load(url: string): Promise<void>;
  runningMode(): Promise<RunningMode>;
};

const { BundleManagerModule } = NativeModules;

export default BundleManagerModule as BundleManagerType;
