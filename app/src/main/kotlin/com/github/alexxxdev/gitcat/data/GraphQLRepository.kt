package com.github.alexxxdev.gitcat.data

import com.github.alexxxdev.gitcat.data.model.User
import com.github.alexxxdev.gitcat.data.model.common.GraphQLData
import com.github.alexxxdev.gitcat.data.model.common.UserData
import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.result.Result

class GraphQLRepository(
    private val authRepository: AuthRepository,
    private val graphQLClient: GithubGraphQLClient
) {
    private var user: User? = null

    init {
        graphQLClient.token = authRepository.getToken()
    }

    fun getUserInfo(login: String): Result<GraphQLData<UserData>, FuelError> {
        val third = graphQLClient.getUserInfo("{\"query\": \"query {" +
                "user(login: \\\"$login\\\") {" +
                "id,login,name,avatarUrl,bio,company,email,location,resourcePath,url,updatedAt," +
                "commitComments{totalCount}" +
                "followers(first: 3){totalCount,nodes{login,name,avatarUrl}}" +
                "following(first: 3){totalCount,nodes{login,name,avatarUrl}}" +
                "gists{totalCount}" +
                "gistComments{totalCount}" +
                "watching{totalCount}" +
                "issues(last: 3){totalCount,nodes{title,repository{id,name,description,nameWithOwner,url,createdAt,parent{id,name,description,nameWithOwner,url,createdAt}}}}" +
                "issueComments{totalCount}" +
                "starredRepositories(first: 3, orderBy: {field: STARRED_AT, direction: DESC}){totalCount,nodes{id,name,description,nameWithOwner,url,createdAt,parent{id,name,description,nameWithOwner,url,createdAt}}}" +
                "pullRequests(last: 3){totalCount,nodes{title,body,repository{id,name,description,nameWithOwner,url,createdAt,parent{id,name,description,nameWithOwner,url,createdAt}}}}" +
                "repositories(first: 3, orderBy: {field: CREATED_AT, direction: DESC}){totalCount,nodes{id,name,description,nameWithOwner,url,createdAt,parent{id,name,description,nameWithOwner,url,createdAt}}}" +
                "repositoriesContributedTo(first: 3, orderBy: {field: CREATED_AT, direction: DESC}){totalCount,nodes{id,name,description,nameWithOwner,url,createdAt,parent{id,name,description,nameWithOwner,url,createdAt}}}" +
                "organizations(first: 100){totalCount,nodes{id,login,name,avatarUrl,email,location,url}}" +
                "pinnedRepositories(first: 3, orderBy: {field: CREATED_AT, direction: ASC}){totalCount,nodes{id,name,description,nameWithOwner,url,createdAt,parent{id,name,description,nameWithOwner,url,createdAt}}}" +
                "}" +
                "}\"}").third
        user = third.component1()?.data?.user
        return third
    }
}