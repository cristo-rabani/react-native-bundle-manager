#import "BundleManager.h"

@implementation BundleManager

@synthesize bridge = _bridge;

RCT_EXPORT_MODULE()

- (void)loadBundle:(NSURL *)url
{
  [_bridge setValue:url forKey:@"bundleURL"];
    
  [_bridge reload];
}

- (void)setHost:(NSString *)host
{
  [_bridge setValue:host forKey:@"bundleURL"];
  NSLog([NSString stringWithFormat:@"setHost%@", host]);
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

RCT_EXPORT_METHOD(setPackagerHost:(NSString*) host) {
  if ([NSThread isMainThread]) {
    [self setHost:host];
  } else {
    dispatch_sync(dispatch_get_main_queue(), ^{
        [self setHost:host];
    });
  }
  return;
}

RCT_EXPORT_METHOD(load:(NSURL*) url) {
    NSLog([NSString stringWithFormat:@"setHost%@", url.absoluteURL]);
  if ([NSThread isMainThread]) {
    [self loadBundle:url];
  } else {
    dispatch_sync(dispatch_get_main_queue(), ^{
        [self loadBundle:url];
    });
  }
  return;
}


@end
