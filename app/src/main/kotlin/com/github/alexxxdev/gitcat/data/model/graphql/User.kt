package com.github.alexxxdev.gitcat.data.model.graphql

import com.github.alexxxdev.gitcat.data.model.common.GraphQLNode
import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: String,
    val login: String,
    val name: String?,
    val avatarUrl: String,
    val bio: String?,
    val company: String?,
    val email: String?,
    val location: String?,
    val resourcePath: String,
    val url: String,
    val updatedAt: String,
    val websiteUrl: String,
    val commitComments: GraphQLNode<Any>,
    val followers: GraphQLNode<UserSmall>,
    val following: GraphQLNode<UserSmall>,
    val gists: GraphQLNode<Any>,
    val gistComments: GraphQLNode<Any>,
    val watching: GraphQLNode<Any>,
    val issues: GraphQLNode<IssueSmall>,
    val issueComments: GraphQLNode<Any>,
    val starredRepositories: GraphQLNode<RepositorySmall>,
    val pullRequests: GraphQLNode<PullRequestSmall>,
    val repositories: GraphQLNode<RepositorySmall>,
    val repositoriesContributedTo: GraphQLNode<RepositorySmall>,
    val organizations: GraphQLNode<OrganizationSmall>,
    val pinnedRepositories: GraphQLNode<RepositorySmall>
)