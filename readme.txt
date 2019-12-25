*To running this project required installed application: 
	+ Docker newest version to run addition docker-compose.yml file
	+ Node 12. to run socket server
*Following steps bellow shows you how to run server
1. Open docker-compose.yml and replace "{your-machine-ip}" with your machine ip in "ipconfig"
2. Then open cmd in current project path at root and types the following in cmd "docker-compose up"
3. Open the IDE for java that had gradle build tool, openning the "LiveServer" project 
   and waiting for gradle synchorization then running application 
4. On cmd, navigate to "NodeService" project, open node-broker then in cmd types "node node-broker.js"
5. Open Android Studio and open "android-client" project, editing file "contraints/Host.java" and replace API_HOST_API, SOCKET_HOST
   with your machine ip. After that you build and run the project
6. End