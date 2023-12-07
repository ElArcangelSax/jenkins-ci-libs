def call()
{
    def exists = fileExists 'appconfig'
    if (exists)
    {
        load "$WORKSPACE/appconfig"
    }
else
    {
        sh 'echo "Appconfig is missing at the root of the project"'
        sh 'exit 1'
    }
}