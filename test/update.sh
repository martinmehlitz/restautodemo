file=$1
endpoint=http://localhost:9000/advert

echo HTTP PUT to $endpoint with contents of $file
curl -vX PUT $endpoint -d @./$file --header "Content-Type: application/json"