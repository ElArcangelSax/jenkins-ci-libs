def call()
{
    // copying credencials
    sh 'echo "---- Obtaining credentials azure export----"'
    //
    env.AZURE_TENANT_ID="${env.azure_tenant}"
    env.AZURE_CLIENT_ID="${env.azure_clientid}"
    env.AZURE_CLIENT_SECRET="${env.azure_secret}"
    env.AZURE_REGION="${env.azure.region}"
    //
    sh 'echo "Loading  credentials region -- $AZURE_REGION --"'
}
