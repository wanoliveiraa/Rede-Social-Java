
# Rede social Parrot 😎 📕

## Descrição 📃
Repositório destinado compartilhamento dos códigos referentes ao backend da aplicação Parrot, feita durante o bootcamp promovido pela Sysmap Solutions,sendo uma api em java com framework spring e com documentação no swagger. 



## Como executar o projeto ✍️
#### Abra o terminal na pasta que contém o arquivo docker-compose.yml e digite o seguinte comando
```bash
  docker compose up -d
```
#### Agora que os contêineres das imagens estão rodando localmente com as configurações do arquivo docker-compose.yml, já é possível utilizar a aplicação.

### ⚠ ⚠ Lembrando, para acessar o LocalStack, use as seguintes informaçõe: 
```bash
  aws configure --profile default
```
```bash
  docker exec -it localhost_demo bash
    AWS Access Key ID [None]: mykey
    AWS Secret Access Key [None]: mykey
    Default region name [None]: us-west-2
    Default output format [None]: json
  root:opt/code/Localstack# aws s3 mb s3://demo-bucket --endpoint -url http://localhost:4566

```
#### Para acessar as urls:
  swagger (http://localhost:8082/swagger-ui.html)  
  postman : (http://localhost:8082)


## Como utilizar 🖱️💻
#### 1º Registre um novo usuário 
![usercreated](https://github.com/bc-fullstack-03/walderney-oliveira-backend/assets/85721450/a1a753d9-371b-4001-8af0-4b361c8c9496)
#### 2º Valide e gerencie o token
![teste auth](https://github.com/bc-fullstack-03/walderney-oliveira-backend/assets/85721450/ef5bddee-e19d-4d2c-85e6-d1786de7f826)
![test concluido](https://github.com/bc-fullstack-03/walderney-oliveira-backend/assets/85721450/b60eb0dd-9207-451c-828f-4c8efba94eab)

#### 3º Adicione o token na autorização no header da requisição
![auth](https://github.com/bc-fullstack-03/walderney-oliveira-backend/assets/85721450/f456c5ad-7107-458e-ac79-adf96babf108)

![requested](https://github.com/bc-fullstack-03/walderney-oliveira-backend/assets/85721450/ac026396-3012-4e73-9e59-a1e015a6b170)

#### 4º Agora você pode explorar a todos os endpoints

![user](https://github.com/bc-fullstack-03/walderney-oliveira-backend/assets/85721450/c066c7c3-b091-4ade-bcb4-4fbcb391b85b)

![post](https://github.com/bc-fullstack-03/walderney-oliveira-backend/assets/85721450/ce80fc9e-a7d5-4852-8a1b-01279bfb35d6)

## Melhorias

Falta a autorização pelo swagger

## 🛠 Habilidades
#### Java, Spring, Docker, Swagger
