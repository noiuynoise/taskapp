echo "setting environment variables for Lambda Function: $1";
aws lambda update-function-configuration --function-name $1 \
    --vpc-config SubnetIds=subnet-0122619829f6cb414,subnet-0b486392bd9ae444d,subnet-0c9248688a4b658a7,SecurityGroupIds=sg-007aa923c6060d14a \
    --environment "Variables={dbPassword=AndrewColbyNikkiZeke,dbUsername=admin,rdsMySqlDatabaseUrl=taskapp.cwysctigob1y.us-east-2.rds.amazonaws.com}"
echo "complete"