# Package
dev.forgepack:library:1.0.0

pom.xml

create sonatype account
claim groupId
prove that you control the namespace (groupId)
    - creating a TXT type DNS record in the domain/namespace/groupId
    - use github namespace
create GPG Key

## Configure settings.xml
~/.m2/settings.xml
<servers>
    <server>
        <id>central</id>
        <username>SEU_USUARIO</username>
        <password>SEU_TOKEN</password>
    </server>
</servers>

## Publish
mvn clean deploy -Pcentral

git tag v1.0.0
git push origin v1.0.0