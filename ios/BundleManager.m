#import "BundleManager.h"
#import "RCTBundleURLProvider.h"

@implementation BundleManager

@synthesize bridge = _bridge;

RCT_EXPORT_MODULE(BundleManagerModule)

- (void)loadBundle:(NSURL *)url
{
  [_bridge setValue:url forKey:@"bundleURL"];

  [_bridge reload];
}

- (void)setHost:(NSString *)host
{
  [[RCTBundleURLProvider sharedSettings] setJsLocation:host];
  [_bridge reload];
}

RCT_EXPORT_METHOD(
                  runningMode:(RCTPromiseResolveBlock)resolve
                  rejecter:(RCTPromiseRejectBlock)reject)
{
  NSString *scheme = [[_bridge bundleURL] scheme];
  BOOL isRemote = [scheme isEqualToString:@"https"];
  resolve(isRemote ? @"REMOTE" : @"LOCAL");
}

RCT_EXPORT_METHOD(
                  setPackagerHost:(NSString*) host
                  resolver:(RCTPromiseResolveBlock)resolve
                  ejecter:(RCTPromiseRejectBlock)reject
) {
  if ([NSThread isMainThread]) {
      resolve(nil);
    [self setHost:host];
  } else {
    dispatch_sync(dispatch_get_main_queue(), ^{
        [self setHost:host];
        resolve(nil);
    });
  }
  return;
}

RCT_EXPORT_METHOD(
                  load:(NSURL*) url
                  resolver:(RCTPromiseResolveBlock)resolve
                  ejecter:(RCTPromiseRejectBlock)reject
) {
    NSLog([NSString stringWithFormat:@"setHost%@", url.absoluteURL]);
  if ([NSThread isMainThread]) {
      resolve(nil);
    [self loadBundle:url];
  } else {
    dispatch_sync(dispatch_get_main_queue(), ^{
        resolve(nil);
        [self loadBundle:url];
    });
  }
  return;
}


@end
