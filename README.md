# JavaとVue.jsで作成するチャットアプリのバックエンドプロジェクト
## データベースの設定
### "postgres"という名前のユーザーでログイン

`psql postgres`

### "java_vue_chat"という名前のデータベースを作成

`create database java_vue_chat`

### データベースに接続

`\c java_vue_chat`


### userテーブルの作成

```
create table users (
 id serial primary key
 , name varchar(100) not null
 , email varchar(100) not null unique
 , password text not null
) ;
```

サンプルユーザーのinsert

`insert into users (name, email, password) values('sample name', 'sample@sample.com', 'samplePassword');`

### messagesテーブルの作成

```
create table messages (
 id serial primary key
 , content text not null
 , user_id integer not null
 , sent_time timestamp default current_timestamp
 , is_delete boolean default false
 , foreign key (user_id) references users(id)
) ;
```

メッセージのサンプルをinsert

`insert into messages (content, user_id) values ('sample content', 1);`

### データベースの削除

`drop table if exists {tableName} cascade;`

### PSQLException: ERROR: permission denied for table と出たとき
原因：データベースへの権限が無い
#### 解決策
`GRANT SELECT, UPDATE, INSERT ON {table_name} TO {user_name};`

### ERROR: permission denied for sequence users_id_seq と出たとき
原因：serial型にinsertする権限が無い

`GRANT USAGE ON SEQUENCE users_id_seq TO {user_name};`

### APIの動作確認
curl  -X POST -H "Content-Type: application/json" -d '{"name": "TestName1", "email": "TestEmail1", "password": "TestPassword1"}' "http://localhost:8080/java_vue_chat/user/insert"
