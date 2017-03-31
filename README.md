## Basic load balancer with token based authentication Application

### Motivation
An Java Application which you can add as many servers as you want and call these results from one place.

### Rest Api Endpoints (with default configuration and localhost)

All endpoint require "Content-Type: application/json" header.

1. Login:

		Type: POST
		Endpoint: /api/auth
		Body: username,password
		Response: token
		Description: get a valid token for other endpoint

2. Machine 

		Type: GET
		Endpoint: api/machines
		Header: X-Auth-Token:{your generated token}
		Response: registered machines
		Description: List your machines
		
		Type: POST
		Endpoint: api/machines
		Header: X-Auth-Token:{your generated token}
		Body: name,url
		Response: registered machine
		Description: Create new machine
		
		Type: PUT
		Endpoint: api/machines/{machineOid}
		Body: oid,name,url
		Response: updated machine
		Description: update your machine
		
		Type: DELETE
		Endpoint: api/machines/{machineOid}
		Response: deleted machine
		Description: delete your machine
		
3. Load balancer

		Type: POST
		Endpoint: api/balances
		Header: X-Auth-Token:{your generated token}
		Body: sring list
		Response: ResponseMessage {machine,message,status}
		Description: trigger your machines

		Type: POST
		Endpoint: api/balances/{MachineNameStartingWith}
		Header: X-Auth-Token:{your generated token}
		Response: ResponseMessage {machine,message,status}
		Description: trigger your machines
		
## Quick Start

1. Start service application

		$ java -jar load-balancer-service.jar (default running at 8080 port)
2. Start server applications (run how much you want)

		$ java -jar load-balancer-server.jar (default running at 8090 port)
		$ java -Dserver.port=8091 -jar load-balancer-server.jar (running at 8091 port)
		
3. Login via username and password to service application

		curl -i -H "Content-Type: application/json" -X POST -d '{"username":"admin","password":"admin"}' http://localhost:8080/api/auth
		
You should get a response like this :

```json		
{
	"token":"eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImF1ZGllbmNlIjoid2ViIiwiY3JlYXRlZCI6MTQ5MDk0Nzg4MDY2OCwiZXhwIjoxNDkxNTUyNjgwfQ.HkyMyUI0xjwZDJUF5-gnJDxoUbvrEtKCRfdKYIFlaDJOSJk-ZmTSkdU_PeFMtHep5HycMWjzO5CUGzR8wMpCrA"
}
```

4. Add your servers to service application as **Machine** (Copy your token to X-Auth-Token header):

		curl -i -H "Content-Type: application/json" -H "X-Auth-Token: eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImF1ZGllbmNlIjoid2ViIiwiY3JlYXRlZCI6MTQ5MDk0Nzg4MDY2OCwiZXhwIjoxNDkxNTUyNjgwfQ.HkyMyUI0xjwZDJUF5-gnJDxoUbvrEtKCRfdKYIFlaDJOSJk-ZmTSkdU_PeFMtHep5HycMWjzO5CUGzR8wMpCrA" -X POST -d '{"name":"Machine-1","url":"http://localhost:8090/api/dummy"}' http://localhost:8080/api/machines
		
		curl -i -H "Content-Type: application/json" -H "X-Auth-Token: eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImF1ZGllbmNlIjoid2ViIiwiY3JlYXRlZCI6MTQ5MDk0Nzg4MDY2OCwiZXhwIjoxNDkxNTUyNjgwfQ.HkyMyUI0xjwZDJUF5-gnJDxoUbvrEtKCRfdKYIFlaDJOSJk-ZmTSkdU_PeFMtHep5HycMWjzO5CUGzR8wMpCrA" -X POST -d '{"name":"Machine-2","url":"http://localhost:8091/api/dummy"}' http://localhost:8080/api/machines
		
		
5. Check your machines.

		curl -i -H "Content-Type: application/json" -H "X-Auth-Token: eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImF1ZGllbmNlIjoid2ViIiwiY3JlYXRlZCI6MTQ5MDk0Nzg4MDY2OCwiZXhwIjoxNDkxNTUyNjgwfQ.HkyMyUI0xjwZDJUF5-gnJDxoUbvrEtKCRfdKYIFlaDJOSJk-ZmTSkdU_PeFMtHep5HycMWjzO5CUGzR8wMpCrA" -X GET http://localhost:8080/api/machines
		
6. Trigger your machines

		curl -i -H "Content-Type: application/json" -H "X-Auth-Token: eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImF1ZGllbmNlIjoid2ViIiwiY3JlYXRlZCI6MTQ5MDk0Nzg4MDY2OCwiZXhwIjoxNDkxNTUyNjgwfQ.HkyMyUI0xjwZDJUF5-gnJDxoUbvrEtKCRfdKYIFlaDJOSJk-ZmTSkdU_PeFMtHep5HycMWjzO5CUGzR8wMpCrA" -X POST -d '["Machine-1","Machine-2"]' http://localhost:8080/api/balances
		
		
7. Or Trigger with "starting with name" regex 

		curl -i -H "Content-Type: application/json" -H "X-Auth-Token: eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImF1ZGllbmNlIjoid2ViIiwiY3JlYXRlZCI6MTQ5MDk0Nzg4MDY2OCwiZXhwIjoxNDkxNTUyNjgwfQ.HkyMyUI0xjwZDJUF5-gnJDxoUbvrEtKCRfdKYIFlaDJOSJk-ZmTSkdU_PeFMtHep5HycMWjzO5CUGzR8wMpCrA" -X POST http://localhost:8080/api/balances/Machine