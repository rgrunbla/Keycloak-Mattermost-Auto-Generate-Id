# Automatic Keycloak Mattermost Id

This is the code of a Keycloak plugin allowing the assignment of a random (long) user id to each user, at account creation (REGISTER event) or at login (LOGIN event).
This eases the interfacing with Mattermost when using Keycloak as a "fake Gitlab", as described on the following websites:

- https://medium.com/@mrtcve/mattermost-teams-edition-replacing-gitlab-sso-with-keycloak-dabf13ebb99e
- https://devopstales.github.io/home/mattermost-keycloak-sso/
- https://qiita.com/wadahiro/items/8b118c34aae904353865

This is experimental, no testing is done, it might kill your pets, …

The code is based on https://github.com/keycloak/keycloak-quickstarts/tree/latest/event-listener-sysout/