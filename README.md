# Thrift Compiler

### 前言

因开发中经常遇到Pull代码后，一堆DTO、Service飘红，所以每当有其他同学新增或修改了Thrift文件，都需要把整个Thrift模块编译一下，费时又费力。（大概是因为我电脑配置不太行吧，每次编译都需要花费好几分钟的时间，刚整理好的编码思路又断了）从 Baidu 到 Google，从 Github 到 ~~Pornhub~~（不是）似乎都没找到能满足我这个懒人小小的愿望的类似工具。于是突发奇想，要不自己撸个插件玩玩吧，所以  Thrift Compiler诞生了！

如果你也有我同样的烦恼，相信这款插件可以让你幸福感倍增 swag~

### 功能

- Pull代码后自动编译有新增或修改的Thrift文件。

- 本地开发时新增或修改Thrift文件可自动编译。

  > 触发条件：
  >
  > - Build Project (Ctrl + F9)
  > - 鼠标焦点移出IntelliJ IDEA

- 选择指定文件或文件夹（支持多选），右键->Thrift Compile。（以上两个自动编译功能基于IntelliJ IDEA 193版本开发，若低于该版本自动编译功能有可能不可用，可选择使用这个手动编译的方法）



懒才是第一生产力  Enjoy it, Enjoy IT （一不小心双了个关 溜了溜了~）

