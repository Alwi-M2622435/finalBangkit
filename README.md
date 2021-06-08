# Project Capstone Bangkit 2021 - CurHad Chatbot
## Cloud Computing Documentation
In this final project we will create an android chatbot application using the ML model deployed in Cloud Run.
### 1. Dockerfile, requirements.txt, .dockerignore
On the [Dockerfile](https://github.com/Alwi-M2622435/finalBangkit/blob/master/Dockerfile) we are copy local code to the container image, and also install production dependencies from requirements.txt  
On the [requirements.txt](https://github.com/Alwi-M2622435/finalBangkit/blob/master/requirements.txt) contain needed dependencies in ML model  
And [.dockerignore](https://github.com/Alwi-M2622435/finalBangkit/blob/master/.dockerignore) contains files to be ignored  

### 2. Build and Deploy the model in Cloud Run  
<code>gcloud builds submit --tag gcr.io/<project_id>/<function_name></code>  
This command will submit the code in Cloud Run and also install all needed dependencies  
  
![image1](https://user-images.githubusercontent.com/79034209/121055332-5ad1be00-c7e7-11eb-84c8-0097b2e8fd81.JPG)

And if the submit is success the message will appear with Status = Success with other details
  
![image5](https://user-images.githubusercontent.com/79034209/121057477-85bd1180-c7e9-11eb-9db0-4ce70286027a.JPG)  
  
<code>gcloud run deploy --image gcr.io/<project_id>/<function_name> --platform managed</code>  
This command will deploys container images to Google Cloud Run, specify the service name, and after that we choose the region
![image2](https://user-images.githubusercontent.com/79034209/121098520-32b28100-c820-11eb-85d6-e7f565727608.JPG)
  
And if the deployment is done, the function will appearn in GCE Cloud Run with name **'getresponse'**
![image4](https://user-images.githubusercontent.com/79034209/121098891-06e3cb00-c821-11eb-9a1b-f4cd06ec0d82.JPG)
  

