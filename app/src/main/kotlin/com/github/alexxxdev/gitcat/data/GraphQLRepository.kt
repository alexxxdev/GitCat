package com.github.alexxxdev.gitcat.data

import com.github.alexxxdev.gitcat.data.model.common.GraphQLData
import com.github.alexxxdev.gitcat.data.model.common.Result
import com.github.alexxxdev.gitcat.data.model.common.UserData

class GraphQLRepository(
    private val authRepository: AuthRepository,
    private val graphQLClient: GithubGraphQLClient
) {

    init {
        graphQLClient.token = authRepository.getToken()
    }

    fun getUserInfo(login: String): Result<GraphQLData<UserData>> {
        val result = graphQLClient.getUserInfo("{\"query\": \"query {" +
                "user(login: \\\"${login}\\\") {" +
                "id,login,name,avatarUrl,bio,company,email,location,resourcePath,url,updatedAt,websiteUrl," +
                "commitComments{totalCount}," +
                "followers(first: 3){totalCount,nodes{login,name,avatarUrl}}," +
                "following(first: 3){totalCount,nodes{login,name,avatarUrl}}," +
                "gists{totalCount}," +
                "gistComments{totalCount}," +
                "watching{totalCount}," +
                "issues(last: 3){totalCount,nodes{title,repository{id,name,description,nameWithOwner,url,createdAt,parent{id,name,description,nameWithOwner,url,createdAt}}}}," +
                "issueComments{totalCount}," +
                "starredRepositories(first: 3, orderBy: {field: STARRED_AT, direction: DESC}){totalCount,nodes{id,name,description,nameWithOwner,url,createdAt,parent{id,name,description,nameWithOwner,url,createdAt}}}," +
                "pullRequests(last: 3){totalCount,nodes{title,body,repository{id,name,description,nameWithOwner,url,createdAt,parent{id,name,description,nameWithOwner,url,createdAt}}}}," +
                "repositories(first: 3, orderBy: {field: CREATED_AT, direction: DESC}){totalCount,nodes{id,name,description,nameWithOwner,url,createdAt,parent{id,name,description,nameWithOwner,url,createdAt}}}," +
                "repositoriesContributedTo(first: 3, orderBy: {field: CREATED_AT, direction: DESC}){totalCount,nodes{id,name,description,nameWithOwner,url,createdAt,parent{id,name,description,nameWithOwner,url,createdAt}}}," +
                "organizations(first: 100){totalCount,nodes{id,login,name,avatarUrl,email,location,url}}," +
                "pinnedRepositories(first: 3, orderBy: {field: CREATED_AT, direction: ASC}){totalCount,nodes{id,name,description,nameWithOwner,url,createdAt,parent{id,name,description,nameWithOwner,url,createdAt}}}," +
                "}" +
                "}\"}")
        authRepository.user = result.value()?.data?.user
        return result
    }
}