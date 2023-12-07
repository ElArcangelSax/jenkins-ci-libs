def call()
{
    //
    if (kustomize_helm && ! kustomize_helm.empty)
    {
        if (cluster && ! cluster.empty)
        {
            sh 'echo "Loading Kube Credentials -- $cluster --"'
            env.KUBECONFIG="$WORKSPACE/$cicd/resources/kube_cred/$cluster"
            //
            writeFile([ file:"$WORKSPACE/check_kube_cred.sh", text:libraryResource("sh/check_kube_cred.sh") ])
            sh 'chmod +x $WORKSPACE/check_kube_cred.sh'
            sh '$WORKSPACE/check_kube_cred.sh'
            sh 'rm $WORKSPACE/check_kube_cred.sh'
        }
    else
        {
            sh '''
                echo "Falta el cluster"
                exit 1
            '''
        }
    }
else
    {
        sh '''
                echo "Falta el kustomize_helm"
                exit 1
        '''
    }
    //
}