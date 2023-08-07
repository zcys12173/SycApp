# SycApp
Android组件化工程（Module可独立运行）

### 使用版本
1. AGP：8.0.2
2. Kotlin-Plugin：1.8.22
3. jdk：17
4. targetSDK：33
5. minSDK：24

### 工程结构
- [build-logic] 通用构建逻辑
- [build-script] 通用构建脚本
- [app] 主工程
- [feature] 业务模块 
  - [feature-common] 通用模块
- [core] 通用基础模块
  - [accessibility] 无障碍模块
  - [image] 图片模块
  - [network] 网络模块
  - [permission] 权限模块
  - [storage] 存储模块
  - [framework] 基础框架模块

### 模块独立运行
修改`build.gradle`中的插件为`application-plugin`
```groovy
plugins {
    id 'application-plugin'
//    id 'library-plugin'
}
```

### AndroidStudio插件

可以安装 [模版代码插件](https://github.com/zcys12173/TemplatePlugin) ，方便快捷创建模版代码,提升开发效率

### Module远端依赖&发布
工程支持发布本地module到maven私服
1. 配置本地maven.properties  

   如果本地没有配置maven.properties，工程依赖全部使用本地依赖方式。 

   工程根目录下创建`maven.properties`文件，配置内容如下：
   
   ```properties
   snapshotUrl=https://packages.aliyun.com/maven/repository/2295471-snapshot-ulWqyW/
   releaseUrl=https://packages.aliyun.com/maven/repository/2295471-release-fIpRog/
   userName=*****
   password=*****
   ```
   
2. 如何切换remote/local依赖  

   根目录 `build.gradle`中配置工程版本，如果有版本号，则使用远端依赖，没有的话使用本地依赖。参考`build.gradle`示例

3. 如何发布  

   发布到maven的`groupId`为`Project.group`，`version`为`Project.version`。在根目录的`build.gradle中配置`,`artifactId`为`Project.name`（插件内自动处理，无需手动配置）。如何配置参考 `build.gradle`示例。

4. `build.gradle`示例  

   ```gradle
   allprojects {
    //maven groupId
    it.group="com.syc.mvvm.libs"
    /*
    * 工程版本
    *
    * 1.如果没有配置，则工程依赖方式为本地module依赖，否则为远端依赖方式(依赖的版本就是当前配置的版本)
    *
    * 2. 当发布新的module的时候，使用此版本号
    */
    switch (it.name){
        case "login":
           // it.version = "1.0.0.3-snapshot"
            break
        case "tiktok":
           // it.version = "1.0.0.3-snapshot"
            break
        case "common":
           // it.version = "1.0.0.2-snapshot"
            break
        case "image":
           // it.version = "1.0.0.2-snapshot"
            break
        case "network":
            // it.version = "1.0.0.1-snapshot"
            break
        case "permission":
            // it.version = "1.0.0.1-snapshot"
            break
        case "storage":
            //it.version = "1.0.0.2-snapshot"
            break
        case "accessibility":
           // it.version = "1.0.0.2-snapshot"
            break
        case "framework":
           // it.version = "1.0.0.1-snapshot"
            break
    }
}
   ```

