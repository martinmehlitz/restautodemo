id=$1
endpoint=http://localhost:9000/advert

echo HTTP DELETE to $endpoint/$id
curl -vX DELETE $endpoint/$id
