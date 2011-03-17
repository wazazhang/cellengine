
del waza.keystore

keytool -genkey ^
-storepass "121121" ^
-keypass "121121" ^
-dname "CN=wazazhang@gmail.com, OU=waza, O=waza, L=waza, S=China, C=CN" ^
-alias waza ^
-keyalg RSA ^
-validity 1000 ^
-keystore waza.keystore
