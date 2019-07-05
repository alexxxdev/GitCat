package com.github.alexxxdev.gitcat.data.model.graphql

import com.github.alexxxdev.gitcat.common.AnySerializer
import com.github.alexxxdev.gitcat.data.model.common.GraphQLNode
import kotlinx.serialization.ContextualSerialization
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
        @Serializable(with = AnySerializer::class) val url: Any,
        val updatedAt: String,
        val websiteUrl: String,
        val commitComments: GraphQLNode<Empty>,
        val followers: GraphQLNode<UserSmall>,
        val following: GraphQLNode<UserSmall>,
        val gists: GraphQLNode<Empty>,
        val gistComments: GraphQLNode<Empty>,
        val watching: GraphQLNode<Empty>,
        val issues: GraphQLNode<IssueSmall>,
        val issueComments: GraphQLNode<Empty>,
        val starredRepositories: GraphQLNode<RepositorySmall>,
        val pullRequests: GraphQLNode<PullRequestSmall>,
        val repositories: GraphQLNode<RepositorySmall>,
        val repositoriesContributedTo: GraphQLNode<RepositorySmall>,
        val organizations: GraphQLNode<OrganizationSmall>,
        val pinnedRepositories: GraphQLNode<RepositorySmall>
)
