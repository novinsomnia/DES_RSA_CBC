# DES_RSA_CBC

## DES部分

加密使用64位明文和64位密钥做为输入，产生64位的密文做为输出。加解密使用相同的密钥。

要求:采用CBC模式对文本加解密

1.对话界面:选择加密或解密,输入密钥,在目录中选择明文或密文文件(内容为二进制,.txt文件,),提示加密或解密完成

2.输出:产生密文文本或明文文本

## RSA部分

编程实现128bit的RSA(包括素数和密钥对产生)，实现传送DES加密算法所要用的密钥，并与DES算法一起形成传送DES加密算法，然后用DES加密算法加密的完整体系。

## src

DES.java 实现DES逻辑部分

RSA.java 实现RSA逻辑部分

DESGUI.java 为DES部分的图形界面（其中的main函数目前被注释掉了）

RSAGUI.java 为默认的实现RSA后的图形界面（含main函数）
