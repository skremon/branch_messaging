package com.remon.branchdemo.data

import java.util.*

/**
 * id: the integer id of the message
    ● thread_id: the integer id of the thread
    ● user_id: the customer id associated with this thread.
    ● agent_id: the id of the customer service agent who wrote this message, or null if this
    message was written by a customer.
    ● body: the body of the message
    ● timestamp: the time when this message was created

 example:
{
    "id": 25,
    "thread_id": 12,
    "user_id": "2035",
    "body": "Ok",
    "timestamp": "2017-02-03T09:06:39.000Z",
    "agent_id": null
},
 */
data class Message(val id: Int,
    val threadId: Int,
    val userId: String,
    val agentId: String?,
    val body: String,
    val timestamp: Date
)
