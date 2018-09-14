
//=====================protobuf协议生成===========================
//protoc.exe编码器位置
cd D:\工具zip包\protoc-3.4.0-win32\protoc-3.4.0-win32\bin

//备注:-I 后面是proto文件所在的目录，  –java_out 后面是生成java文件存放地址 //最后一行是proto文件的名称，可以写绝对地址，也可以直接写proto文件名称
protoc -I=D:\工具zip包\protoc-3.4.0-win32\protoc-3.4.0-win32\bin --java_out=D:\工具zip包\protoc-3.4.0-win32\protoc-3.4.0-win32\bin oldProtobufProtocol.proto

//生成新协议文件.
protoc  --java_out=D:\工具zip包\protoc-3.4.0-win32\protoc-3.4.0-win32\bin newProtobufProtocol.proto


//备注：protobuf-java.3.6.1.jar