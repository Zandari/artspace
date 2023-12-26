package com.example.myapp.data

data class User(
    val id: Int,
    val name: String,
    val imageUrl: String,
);

data class Publication(
    val id: Int,
    val title: String,
    val imageUrl: String,
    val description: String,
    val owner: User,
);

data class Group(
    val id: Int,
    val title: String,
    val description: String,
    val coverUrl: String,
);


object RepositoryInstance {
    val blankPublication: Publication = Publication(0, "", "", "", User(0, "", ""))
    val blankGroup: Group = Group(0, "", "", "")

    private var service = null

    suspend fun getGroup(id: Int): Group =
        Group(
            id,
            "Title",
            "Description",
            "https://images.unsplash.com/photo-1682686580452-37f1892ee5e8?q=80&w=1975&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDF8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"
        );

    suspend fun getPublications(groupId: Int) =
        List(10) {
            getPublication(it)
            Publication(
                it,
                "Image title",
                "https://images.unsplash.com/photo-1703533136832-2e91ef1a746f?q=80&w=2080&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                "Description",
                User(1, "username", "")
            )
        }

    suspend fun getPublications(group: Group) = getPublications(group.id)

    suspend fun getPublication(pubId: Int): Publication {
        return Publication(
            pubId,
            "Image title",
            "https://images.unsplash.com/photo-1703533136832-2e91ef1a746f?q=80&w=2080&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
            "Description",
            User(1,
                "zandari",
                "https://images.unsplash.com/photo-1703566757295-1a72f47a94de?q=80&w=1469&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D")
        )
    }

}