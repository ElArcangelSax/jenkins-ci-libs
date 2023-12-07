# jenkins-ci-libs
Jenkins for N5 Challenge


First of all, it's very important that you follow these steps:

1) Sign in to Azure Portal: Sign in to the Azure Portal with the Azure account.
2) Navigate to Azure Active Directory: In the left-hand menu, select "Azure Active Directory."
3) Go to App registrations: Within Azure Active Directory, select "App registrations."
4) Register a new application: Click on "New registration." Provide a name for your application ("n5challenge") and select the type of account it will use  ("Accounts in this organizational directory only").
5) Save the information: After registering the application, Azure will automatically generate an Application (client) ID and Directory (tenant) ID. Note down these values, as they will be our AZURE_CLIENT_ID and AZURE_TENANT_ID, respectively.
6) Create a secret: In the "Certificates & Secrets" section, create a new client secret and make a note of the generated value. This will be your AZURE_CLIENT_SECRET.
7) Assign permissions: Assign the necessary permissions to your Service Principal application so that it can access the Azure resources you need.
8) Assign to subscription: In the "Subscriptions" section, select your subscription and associate the newly created Service Principal application with it.

Once you have completed these steps, you will have all the environment variables (AZURE_CLIENT_ID, AZURE_TENANT_ID, and AZURE_CLIENT_SECRET) when interacting with Azure CLI or in any other scenario where Azure authentication is required.


To retrieve your AZURE_SUBSCRIPTION_ID , you can use the following command in the Azure CLI:
az account show --query id --output tsv

VERY IMPORTANT: FIRSTLY MAKE SURE to have the plugins installed:
    a) role-based Authorization Strategy
    b) Azure VM agents (to provision and deprovision agents)

and
    c) Configuring  Github with WebHooks
        i) Add webhook -> Payload URL: http://....... and content-type JSON
    d) In Jenkins Create Item Name (to test Github hook for PUSH with a branch for this challenge)
        i) GIT -> Repository URL: (the same)
        