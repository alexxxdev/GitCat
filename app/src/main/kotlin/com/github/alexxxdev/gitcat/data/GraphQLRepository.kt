package com.github.alexxxdev.gitcat.data

import com.github.alexxxdev.fuelcomfy.SerializationStrategy
import com.github.alexxxdev.fuelcomfy.setInterface
import com.github.alexxxdev.gitcat.data.model.common.Error
import com.github.alexxxdev.gitcat.data.model.common.GraphQLData
import com.github.alexxxdev.gitcat.data.model.common.Result
import com.github.alexxxdev.gitcat.data.model.common.UserData
import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.core.FuelManager
import kotlinx.serialization.json.Json

class GraphQLRepository(
    private val authRepository: AuthRepository
) {
    val service: GithubGraphQLService
    val token: String
    init {
        token = authRepository.getToken().orEmpty()
        FuelManager.instance.basePath = "https://api.github.com"
        service = FuelManager.instance.setInterface(GithubGraphQLService::class)
        SerializationStrategy.json = Json.nonstrict
    }

    suspend fun getUserInfo(login: String): Result<GraphQLData<UserData>> {
        val result = service.getUserInfo(
            token, "{\"query\": \"query {" +
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

        return if (result.component2() != null) {
            Result.error(Error.of((result.component2() as FuelError).response.statusCode, result.component2() as? FuelError))
        } else {
            if (result.component1()?.data == null) {
                Result.error(Error.of(0, result.component1()?.errors))
            } else {
                authRepository.user = result.component1()?.data?.user
                Result.of(result.component1())
            }
        }
    }
}