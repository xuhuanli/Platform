#项目简介

##app/base

项目中的所有基类

##app/thirdlibrary

部分第三方库的封装

##read 

项目阅读模块

##discovery

项目发现模块

##service

项目服务模块(not system service)

##info

项目个人信息模块

##login

项目隐藏的登录模块

##packlibrary

项目部分第三方库的简单封装

##container
项目中的外层容器类

###MyApplicationLike

应用的全局配置Application

###Constant

应用的全局常量类

## bug recording 

1. 轮播图在下拉刷新的时候，并不会进行请求刷新 
    bug原因：没有进行bannerData的访问请求，下拉刷新的时候只做了文章请求
          （暂时没时间搞这个了）




