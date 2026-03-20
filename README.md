# Connectly

A project requirement for MOIT149

## Dependencies
- Auth0
- MongoDB

## Environment Variables
Either create a new properties.env file or set the following environment variables:
    DB_CONNECTION_STRING="Database connection string to a MongoDB database"
    
    OAUTH_CLIENT_ID="Auth0 client ID"
    OAUTH_CLIENT_SECRET="Auth0 client secret"
    
    S3_BUCKET_NAME="Bucket name for the S3 Compliant Storage"
    S3_PUBLIC_URL="Public URL for the S3 Compliant Storage"
    S3_ACCOUNT_ID="S3 account ID"
    S3_ACCESS_KEY_ID="S3 access key ID"
    S3_SECRET_ACCESS_KEY="S3 secret access key"