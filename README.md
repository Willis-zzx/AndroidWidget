# AndroidWidget
整理一些开发过程中用到的Android组件、代码 

## MQTT

MQTT文件夹中是一个基于MQTT通信协议的小demo

demo的功能包含：

- 连接MQTT服务器
- 订阅主题
- 发布消息

## TCP

TCP文件夹下是一个基于TCP通信协议的小demo

demo的功能包含：

1. TCP服务器端
   - 连接TCP服务器
   - 发送消息
   - 接收消息
2. TCP客户端
   - 构建TCP服务器
   - 发送消息
   - 接收消息

## Chart

Chart文件夹下一个图表小demo

没有设置跳转按钮 ，自行在AndroidMainfest.xml文件中修改启动的第一个页面

- MainActivity：一个以ListView为基础的时间轴
- MainActivity2：一个以ListView为基础的下拉刷新
- MainActivity3：分享模块
- MainActivity4：仪表盘，数字不同，仪表盘的指针、数字、背景颜色都会进行变化
- MainActivity5：实时曲线图（只实现了一条实时曲线）
- MainActivity6：按钮添加数据到曲线图中；实时曲线图（可以有多条曲线）
- MainActivity7：接收MQTT传来的数据并绘制实时曲线图（可以有多条曲线）

## 。。。