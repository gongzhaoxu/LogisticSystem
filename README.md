# **东北大学2019级移动开发大作业：物流管理系统**

## 一.功能：

1. 用户注册，登录
2. .存储用户信息至本地
3. 录入运单到本地Sqlite数据库
4. 查看本地数据库运单数据
5. 解析服务器XML运单数据
6. 解析服务器JSON运单数据

## 二.项目文件树

│  AndroidManifest.xml
│  test
│  
├─java
│  └─com
│      └─example
│          └─logisticsystem
│              │  AddNewAddressActivity.java
│              │  CompleteUser.kt
│              │  Login.kt
│              │  LogisticAdapter.kt
│              │  LogisticView.kt
│              │  MainActivity.kt
│              │  MyApplication.kt
│              │  MyDatabaseHelper.kt
│              │  RegisterActivity.kt
│              │  User.kt
│              │  WebViewActivity.kt
│              │  
│              └─ui
│                  │  SharedViewModel.kt
│                  │  
│                  ├─input
│                  │      InputFragment.kt
│                  │      InputViewModel.kt
│                  │      
│                  ├─mine
│                  │      MineFragment.kt
│                  │      MineViewModel.kt
│                  │      
│                  └─query
│                          QueryFragment.kt
│                          QueryViewModel.kt
│                          
└─res
    ├─drawable
    │      avatar.jpg
    │      avatar1.png
    │      avatar_boy_1.xml
    │      avatar_boy_2.xml
    │      avatar_boy_3.xml
    │      avatar_boy_4.xml
    │      avatar_boy_5.xml
    │      avatar_boy_6.xml
    │      avatar_girl_1.xml
    │      avatar_girl_2.xml
    │      avatar_girl_3.xml
    │      avatar_girl_4.xml
    │      avatar_girl_5.xml
    │      avatar_girl_6.xml
    │      bg_radius.xml
    │      btn_radius.xml
    │      editview_border.xml
    │      grid_radius.xml
    │      icon_full.xml
    │      ic_arrow_right.xml
    │      ic_delete.xml
    │      ic_fatal_error.xml
    │      ic_file.xml
    │      ic_input_black_24dp.xml
    │      ic_json.xml
    │      ic_launcher_background.xml
    │      ic_mine_black_24dp.xml
    │      ic_query_black_24dp.xml
    │      ic_right.xml
    │      ic_right_arrow.xml
    │      ic_wrong.xml
    │      ic_xml.xml
    │      input_radius.xml
    │      password_icon.png
    │      user_icon.png
    │      
    ├─drawable-v24
    │      ic_launcher_foreground.xml
    │      
    ├─layout
    │      activity_complete_user.xml
    │      activity_login.xml
    │      activity_logistic_view.xml
    │      activity_main.xml
    │      activity_register.xml
    │      activity_webview.xml
    │      fragment_input.xml
    │      fragment_mine.xml
    │      fragment_query.xml
    │      logistic_view_item.xml
    │      
    ├─menu
    │      bottom_nav_menu.xml
    │      
    ├─mipmap-anydpi-v26
    │      ic_launcher.xml
    │      ic_launcher_round.xml
    │      
    ├─mipmap-hdpi
    │      ic_launcher.webp
    │      ic_launcher_round.webp
    │      
    ├─mipmap-mdpi
    │      ic_launcher.webp
    │      ic_launcher_round.webp
    │      
    ├─mipmap-xhdpi
    │      ic_launcher.webp
    │      ic_launcher_round.webp
    │      
    ├─mipmap-xxhdpi
    │      ic_launcher.webp
    │      ic_launcher_round.webp
    │      
    ├─mipmap-xxxhdpi
    │      ic_launcher.webp
    │      ic_launcher_round.webp
    │      
    ├─navigation
    │      mobile_navigation.xml
    │      
    ├─values
    │      colors.xml
    │      dimens.xml
    │      strings.xml
    │      themes.xml
    │      
    └─values-night
            themes.xml
            
