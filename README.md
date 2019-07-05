# LottieDemo
1:dependencies 下添加
compile 'com.airbnb.android:lottie:2.0.0'

这个用最高版本  不兼容android X


2:lottie这个第三方库提供了很多方法  ,供上层调用
  playAnimation()  开始动画
  reverseAnimation()  动画反向执行
  cancelAnimation  取消动画
  setProgress   设置动画显示进度  0f-1f
  addAnimatorListener  添加动画监听等
  app:lottie_autoPlay=“true”  是否自动播放
  app:lottie_fileName= “data.json”  关联需要的json
  这些基本操作就够用了
  
  
 3：适配问题  
 
     因为lottie内置是dp，我这个是设计师给ios设计的2X的 ，所以为了能正常显示，我就加了一个缩放
     app:lottie_scale=“0.5”
 
4：版本适配  之前我们设计用的是5.5的开发版本  导致我这边出现了 java.lang.IllegalStateException: Missing values for keyframe 这个异常
   后来将设计版本改成5.3  正常运行
   


