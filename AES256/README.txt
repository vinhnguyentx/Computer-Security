UTEID: ttn2365; vtn288;
FIRSTNAME: Thanh; Vinh;
LASTNAME: Nguyen; Nguyen;
CSACCOUNT: thanhcs; vinhcs;
EMAIL: thanhnguyencs@utexas.edu; vinhnnguyentx@gmail.com;

[Program 4]
[Description]
There are 8 java file in our program.
AES.java: contains the parse input (option, keyFile name, inputFile name). The method to check if the key is malformed. It will output the time to run.
AESCrypto.java: parent class of AESDecrypt and AESEncrypt. Contains all of the method used by both classes. (i.e. addRoundKey)
AESEncrypt.java: received a plaintext string and encrypt it by using the key expansion from KeyExpansion class (Each line of the inputFile will have 1 object of this class)
AESDecrypt.java: Same as AESEncrypt class, but it receives a line of cipher instead (Each line of cipher will have 1 object of this class)
Constants.java: used to keep all of constant data (S-Box, invert S-Box, ...)
DataCrypto.java: parses the input line into an 4x4 array and create AESEncrypt object or AESDecrypt object according to the option of command line
KeyExpansion.java: its object receives a string key line and converted it into an array of key expansion. (1 object for a key)
Word.java: created to follow the instruction of AES Standard (its under data is an array of 4 byte = a word)

We used mixColumns code from professor.

With a file 33MB (inputFile has 1000000 lines)- I think it actually depends on computer's hardward:
Encrypt speed: 6.448 MB/s
Decrypt speed: 4.958 MB/s

[Finish]
We finished all of the requirements

[Test Cases]
[Input of test 1]
[command line]
java AES e keyFile testcase1
java AES d keyFile testcase1.enc

keyFile
testcase1

[Output of test 1]
testcase1.enc
testcase1.enc.dec

[Input of test 2]
[command line]
java AES e keyFile testcase2
java AES d keyFile testcase2.enc

keyFile
testcase2

[Output of test 2]

testcase2.enc
testcase2.enc.dec

[Input of test 3]
[command line]
java AES e keyFile testcase3
java AES d keyFile testcase3.enc

keyFile
testcase3

[Output of test 3]
testcase3.enc
testcase3.enc.dec

[Input of test 4]
[command line]
java AES e keyFile testcase4
java AES d keyFile testcase4.enc

keyFile
testcase4

[Output of test 4]
testcase4.enc
testcase4.enc.dec

