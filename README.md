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

   工程根目录下创建`maven.properties`文件，配置内容如下：
   
   ```properties
   snapshotUrl=https://packages.aliyun.com/maven/repository/2295471-snapshot-ulWqyW/
   releaseUrl=https://packages.aliyun.com/maven/repository/2295471-release-fIpRog/
   userName=*****
   password=*****
   ```  
   如果本地没有配置maven.properties，工程依赖全部使用本地依赖方式。
   
3. 如何切换remote/local依赖  

   根目录 `build.gradle`中配置subProject.description的值；LOCAL:本地依赖，REMOTE：远端依赖。参考`build.gradle`示例

4. 如何发布  

   发布到maven的`groupId`为`Project.group`，`version`为`Project.version`。在根目录的`build.gradle中配置`,`artifactId`为`Project.name`（插件内自动处理，无需手动配置）。如何配置参考 `build.gradle`示例。

5. `build.gradle`示例  

   ```gradle
   allprojects {

    /*
    * 配置Project的group、version、description，主要用于工程依赖和仓库发布
    *
    * Project.group：本地Module发布到maven仓库的GroupId、本地依赖远端module的仓库的GroupId
    *
    * Project.version：本地Module发布到maven仓库的版本号、本地依赖远端module的的版本号
    *
    * Project.description：控制module的依赖方式，LOCAL:本地依赖，REMOTE：远端依赖
    *
    * Project.name：本地Module发布到maven仓库的名称、本地依赖远端module的仓库的名称
    *
    * 发布&远端依赖的maven配置：group:name:version;（参考MavenConfig.kt,ProjectDependenciesConfig.kt）
    *
    * 注：如果没有配置version则会强制使用本地依赖
    */
    it.group = "com.syc.mvvm.libs"
    switch (it.name) {
        case "login":
            it.version = "1.0.0.3-snapshot" //版本号
            it.description = "REMOTE"       //依赖方式，LOCAL:本地依赖，REMOTE：远端依赖
            break
        case "tiktok":
            it.version = "1.0.0.3-snapshot"
            it.description = "LOCAL"
            break
        case "common":
            it.version = "1.0.0.2-snapshot"
            it.description = "REMOTE"
            break
        case "image":
            it.version = "1.0.0.2-snapshot"
            it.description = "REMOTE"
            break
        case "network":
            it.version = "1.0.0.1-snapshot"
            it.description = "REMOTE"
            break
        case "permission":
            it.version = "1.0.0.1-snapshot"
            it.description = "REMOTE"
            break
        case "storage":
            it.version = "1.0.0.2-snapshot"
            it.description = "REMOTE"
            break
        case "accessibility":
            it.version = "1.0.0.2-snapshot"
            it.description = "REMOTE"
            break
        case "framework":
            it.version = "1.0.0.1-snapshot"
            it.description = "REMOTE"
            break
    }
}
   ```

