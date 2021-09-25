# Installation
## Git
<code>$ sudo apt update</code><br>
<code>$ sudo apt install git</code><br>
<code>$ ssh-keygen -t rsa -b 4096 -C "(own email address)"</code><br>
<code>$ git config --global user.name "(own user name)"</code><br>
<code>$ git config --global user.email "(own user email)"</code><br>
<code>$ git config -l</code><br>
<code>$ git clone (url of the Github repository)</code><br>
<code>$ git status</code><br>

## Java JDK and Maven
<code>$ sudo apt update</code><br>
<code>$ sudo apt install maven</code><br>
<code>$ mvn -v</code><br>
<code>$ sudo apt-get install openjdk-11-jre</code><br>
<code>$ sudo apt-get install openjdk-11-jdk</code><br>
<code>$ java -version</code><br>

## Maria DB
<code>$ sudo apt install mariadb-server</code><br>
<code>$ mysql -u root -p</code><br>

<p>Change the root password!</p>

### Initialization
<code>MariaDB [(none)]> show databases;</code><br>
<code>MariaDB [(none)]> CREATE DATABASE IF NOT EXISTS webshop;</code><br>
<code>MariaDB [(none)]> CREATE USER user IDENTIFIED BY 'user';</code><br>
<code>MariaDB [(none)]> GRANT ALL PRIVILEGES ON webshop.* TO user;</code><br>
<code>MariaDB [(none)]> show grants for user;</code><br>

### Usages
#### Login as user
<code>$ mysql -u user -p</code><br>
<code>Enter password:</code><br>
<code>MariaDB [(none)]> use webshop</code><br>
<code>Database changed</code><br>
<code>MariaDB [webshop]> show tables;</code><br>
<code>MariaDB [webshop]> select * from customers;</code><br>
