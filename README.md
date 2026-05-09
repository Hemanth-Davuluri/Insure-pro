insurance platform project for my info 

routed through api gateway 

to register:
Post : localhost:8010/auth/register
body :
{
  "userName": "DavuluriHemanth",
  "password": "Hemanth12*",
  "email": "davulurihemanthsaikumar@gmail.com",
  "phoneNumber": "8328534379",
  "roles": "admin"
}

to login :
Post : localhost:8010/auth/login
body:
{
  "username": "DavuluriHemanth",
  "password": "Hemanth12*"
}

this login give bearer token which will expries in hour

to create coverage rule ==> we can create for Health, Life, Vehicle 
Post : http://localhost:8010/policy/CoverageRules/createCoverageRule
sample request body :
{
    "coverageType": "Vehicle",
    "requiredFields": [
        "vehicleNumber",
        "vehicleAge",
        "licenseNumber"
    ],
    "premium": 12000.0,
    "tax": 18.0
} 

to subscribe to policy :
Post : http://localhost:8010/policy/policySubscription/subscribingToPolicy?submit=true
sample request body :
{
  "customerId": 101,
  "coverageType": "Vehicle",
  "payLoad": {
    "vehicleNumber": "AP07AB1234",
    "vehicleAge": "3",
    "licenseNumber": "DL12345678"
  }
}
