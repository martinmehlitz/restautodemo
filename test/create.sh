file=$1
endpoint=http://localhost:9000/advert

echo HTTP POST to $endpoint with contents of $file
curl -vX POST $endpoint -d @./$file --header "Content-Type: application/json"