# 项目简介

乐乐物流系统是东北大学2019级移动开发技术课程的结课作业，以kotlin为开发语言，结合学过的activity、UI布局、多线程开发、数据库管理、跨程序数据共享、网络技术、Jetpack等技术完成了一个物流管理系统。作者：计算机1904班巩钊旭，学号20194711。

特点：

1. UI美观，控件丰富，在不同机型下有完美的适配；主题明确，软件整体采用浅绿色，体现出此物流系统的绿色。
2. 开发符合规范，主Activity由三个Fragment组成，采用了谷歌官方推荐的模型控制分离设计；项目用的的颜色在colors.xml中定义，共用的布局大小在dimen.xml中定义，组件的文字在strings.xml中定义而不是在布局文件中定义。
3. 控件事件绑定统一采用View Binding模式，因此不存在因视图 ID 无效而引发 Null 指针异常的风险，并且具有类型安全性。

基础功能（**所有交付功能均完成**）：

1. 数据库用户表和运单表的设计与创建
2. 时间根据系统时间刷新
3. 任意注册用户可使用系统。
4. 录入运单
5. 查询本机运单
6. 查询公司XML运单
7. 查询公司JSON运单
8. 切换用户
9. 运单数据超出一屏，可滚动显示。
10. 运单表中不可空信息的判断

额外完成的功能：

1. **用户注册（每个用户都有属于自己的头像）**
2. **用户信息完善**
3. **运单信息的指定可视化删除**
4. <u>**主Activity使用Fragment技术，而非几个activity来回跳转，用户点击底部导航栏即可实现Fragment无缝切换，更符合用户使用习惯。**</u>
5. **录入运单的收发货地址使用citypicker，不用用户自己填写信息，直接选择即可。**
6. **本机运单清空**

***以上功能中，利用Fragment实现底部导航的功能最为复杂，也是我编写时间最久的部分。***

代码讲解在视频中，具体源代码见附件“配置文件和源代码”

# 一.Activity

1. 登录(Login)

   <img src="https://github.com/gongzhaoxu/LogisticSystem/blob/master/README_SRC/image-20220528195836767.png" alt="image-20220528195836767" style="width:300px;" />

2. 注册(RegisterActivity)

   <img src="https://github.com/gongzhaoxu/LogisticSystem/blob/master/README_SRC/image-20220528200006604.png" alt="image-20220528200006604"  style="width:300px;" />

3. 我的信息(MineFragment)

   <img src="https://github.com/gongzhaoxu/LogisticSystem/blob/master/README_SRC/image-20220528200127290.png" alt="image-20220528200006604"  style="width:300px;"  />

4. 录入运单(InputFragment)

   <img src="https://github.com/gongzhaoxu/LogisticSystem/blob/master/README_SRC/image-20220528200352956.png" alt="image-20220528200352956"  style="width:300px;"  />

5. 查询运单(QueryFragment)

   <img src="https://github.com/gongzhaoxu/LogisticSystem/blob/master/README_SRC/image-20220528200433388.png" alt="image-20220528200433388"  style="width:300px;"  />

6. 完善用户信息(CompleteUser)

   <img src="https://github.com/gongzhaoxu/LogisticSystem/blob/master/README_SRC/image-20220528200502316.png" alt="image-20220528200502316"  style="width:300px;"  />

7. 运单显示界面(LogisticView)

   <img src="https://github.com/gongzhaoxu/LogisticSystem/blob/master/README_SRC/image-20220528200544867.png" alt="image-20220528200544867"  style="width:300px;"  />

8. 作者信息(WebViewActivity显示网页)

   <img src="https://github.com/gongzhaoxu/LogisticSystem/blob/master/README_SRC/image-20220528200631249.png" alt="image-20220528200631249"  style="width:300px;"  />

   **总计：8个**

   


# 二.数据库展示

物流表结构：	

```kotlin
"create table Logistic (" +
        "id integer primary key autoincrement," +
        "src text," +
        "dest text," +
        "senderName text," +
        "senderTel text," +
        "accepterName text," +
        "accepterTel text," +
        "itemName text," +
        "itemNum text," +
        "payAlready text," +
        "payDest text)"
```

![image-20220528201934477](https://github.com/gongzhaoxu/LogisticSystem/blob/master/README_SRC/image-20220528201934477.png)

用户信息表结构：

```kotlin
"create table User (" +
        "user_id integer primary key autoincrement," +
        "user_department text," +
        "user_name text," +
        "user_login text," +
        "user_passwd text," +
        "user_tel text)"
```

![image-20220528202253106](https://github.com/gongzhaoxu/LogisticSystem/blob/master/README_SRC/image-20220528202253106.png)
