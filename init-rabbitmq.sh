#!/bin/sh

echo "Aguardando RabbitMQ ficar pronto..."
sleep 10

echo "Criando exchange e queue no RabbitMQ..."

# Criar exchange
curl -i -u guest:guest -H "content-type:application/json" \
  -X PUT http://rabbitmq:15672/api/exchanges/%2F/consulta_exchange \
  -d'{"type":"direct","durable":true}'

# Criar queue
curl -i -u guest:guest -H "content-type:application/json" \
  -X PUT http://rabbitmq:15672/api/queues/%2F/consulta_queue \
  -d'{"durable":true}'

# Criar binding
curl -i -u guest:guest -H "content-type:application/json" \
  -X POST http://rabbitmq:15672/api/bindings/%2F/e/consulta_exchange/q/consulta_queue \
  -d'{"routing_key":"consulta.new"}'

echo "RabbitMQ configurado com sucesso!"

