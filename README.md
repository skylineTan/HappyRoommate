# HappyRoommate
###一、下载方式
1.[点击此处通过360手机助手下载app](http://zhushou.360.cn/detail/index/soft_id/3082742?recrefer=SE_D_%E5%BE%AE%E5%AE%A4#nogo)       
2.手机扫描二维码下载        
![image](https://github.com/skylineTan/HappyRoommate/blob/master/Art/%E5%BE%AE%E5%AE%A4%E4%BA%8C%E7%BB%B4%E7%A0%81.png)      
###二、应用目的
开始的时候看到QQ空间有情侣空间的功能，没有多少人使用，本来想写一个情侣空间的，但是类似的app太多，所以最后还是决定写了这个以寝室为中心的类似于寝室空间的app。
###三、主要功能：
+  利用bmob实现的登录、注册和数据、文件存储
+  提供昵称、用户名、email关注好友，获得粉丝列表，修改个人信息，创建、添加寝室，点击查看好友个人资料      
![image](https://github.com/skylineTan/HappyRoommate/blob/master/Art/screenshot2.png)![image](https://github.com/skylineTan/HappyRoommate/blob/master/Art/screenshot4.png)
+  点击抽屉切换登录,暂不支持管理账号      
![image](https://github.com/skylineTan/HappyRoommate/blob/master/Art/screenshot6.png)
+  登录注册后自动关注本人，本人账号作为公众号推送和介绍app用法和功能，可以获取关注列表，暂不支持查询粉丝列表和取消关注。     
![image](https://github.com/skylineTan/HappyRoommate/blob/master/Art/screenshot1.png)       
模块
+  广场模块：广场模块看所有人的文章，分享你所有想分享的东西，寝室经历、感动瞬间等，必须带一张图。支持查看文章详情。头部的roolviewpager的配图发现和我的app风格和主题差不多就直接用了，广场文章支持发布者删除。
+  动态模块：动态界面是你和你关注的人的动态，支持多图，单图，纯文字。同一个寝室的动态会在同一个空间里面，动态是以寝室的名义发表，显示的是发布人的头像和寝室的名字。进入空间可以看到寝室每个人发布的动态，实现的是在同一个寝室在同一个空间里面。动态只有发布者有权限删除
+  便利贴模块：便利贴界面需要创建或加入一个寝室，只有寝室内部可以看到，有寝室之后就可以发布便利贴，当你不在家外出的时候，一个寝室的人都可以看到，当每个人看寝室里的人有什么通知或事的时候，第一个想到的应该是本app，而不是其他的，给了用户一个不卸载的理由。
+  支持进入别人空间查看别人寝室的动态，但不支持关注非好友的人，只能看到头像，类似于微信，保护隐私。
+  新加入别的寝室，自动和原来寝室解除关系。
+  尚未开发的模块：
    +  1.利用bmob加密，实现私密空间，只能寝室的人看得到，分类娱乐、运动、美食等模块，随时随地和寝室里的人分享动态。
    +  2.聊天功能，只支持一个寝室群聊，当寝室人聊天，为了查看重要信息，第一个想到的是本app，而不是微信、QQ，新增一个用户不卸载的理由。
    +  3.抽屉的生日模块：寝室内部人生日提醒，照片墙生日照片，可以以后回忆。
下面是应用截图：
![image](https://github.com/skylineTan/HappyRoommate/blob/master/Art/img6.png)![image](https://github.com/skylineTan/HappyRoommate/blob/master/Art/img7.png)![image](https://github.com/skylineTan/HappyRoommate/blob/master/Art/img8.png)![image](https://github.com/skylineTan/HappyRoommate/blob/master/Art/img9.png)![image](https://github.com/skylineTan/HappyRoommate/blob/master/Art/img10.png)        
代码存在的问题：
1.字符串没有全部放到string里面
2.发布前没有出现问题，发布之后我测试有可能会修改不了资料的问题，当好友没有发表动态可能无法查看个人空间，新用户发布便利贴可能要先退出，在登录便利贴是发布了的。
3.bmob的查询语句由于是以寝室为中心，可能代码有点多。
4.edittext不能收键盘，影响用户体验
