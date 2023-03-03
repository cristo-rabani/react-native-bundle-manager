export declare type RunningMode = 'LOCAL' | 'REMOTE';
declare type BundleManagerType = {
    setPackagerHost(host: string): Promise<void>;
    load(url: string): Promise<void>;
    runningMode(): Promise<RunningMode>;
};
declare const _default: BundleManagerType;
export default _default;
