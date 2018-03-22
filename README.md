#  Wave水波纹动画
## 使用如下：
### 在build文件中添加
```groovy
allprojects {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}
```
### 然后在build文件的dependencies里边添加：
```groovy
dependencies {
    compile 'com.github.funaifu:Wave:1.0'
}

```
### 在XML文件中添加：
```xml
 <com.view.wave.WaveView
       android:id="@+id/wave"
       app:wave_stroke_width="10dp"
       app:wave_stroke_color="@color/colorPrimary"
       app:wave_crest="80dp"
       app:wave_length="100dp"
       android:layout_width="match_parent"
       android:layout_height="match_parent"/>
```
### 然后直接调用：
```java
WaveView.start();
```
就能播放水波纹动画了。



