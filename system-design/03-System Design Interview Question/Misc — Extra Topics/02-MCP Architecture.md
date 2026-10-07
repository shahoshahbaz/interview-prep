# MCP Architecture

## Table of Contents
1. [Overview](#1-overview)
2. [Step 1 - Tool Discovery and Initialization](#2-step-1---tool-discovery-and-initialization)
3. [Step 2 - User Request Submission](#3-step-2---user-request-submission)
4. [Step 3 - Send Enriched Context to LLM](#4-step-3---send-enriched-context-to-llm)
5. [Step 4 - LLM Intent Analysis and Tool Selection](#5-step-4---llm-intent-analysis-and-tool-selection)
6. [Step 5 - Tool Invocation via MCP Server](#6-step-5---tool-invocation-via-mcp-server)
7. [Step 6 - External API or Data Source Execution](#7-step-6---external-api-or-data-source-execution)
8. [Step 7 - Tool Response Retrieval (Raw Data)](#8-step-7---tool-response-retrieval-raw-data)
9. [Step 8 - Send Tool Output to LLM for Post-Processing](#9-step-8---send-tool-output-to-llm-for-post-processing)
10. [Step 9 - LLM Response Formatting and Generation](#10-step-9---llm-response-formatting-and-generation)
11. [Step 10 - Deliver Final Response to User](#11-step-10---deliver-final-response-to-user)
12. [Final Flow (Quick View)](#12-final-flow-quick-view)
13. [Key Architectural Insights](#13-key-architectural-insights)
14. [One-Line Summary](#14-one-line-summary)

---

## 1. Overview

This architecture explains how an MCP-based agent system handles a user request end to end:
- The agent discovers available tools from MCP Server.
- The LLM chooses which tool to call.
- MCP Server executes the real external call.
- The LLM formats the final response for the user.

---

## 2. Step 1 - Tool Discovery and Initialization

- MCP Client (Agent) starts up.
- Agent requests available tools from MCP Server.
- MCP Server returns tool definitions:
    - tool name
    - description
    - input parameters
- Outcome: the agent now knows which tools it can use.

---

## 3. Step 2 - User Request Submission

- User sends a natural-language request to the MCP Client.

Example request:
`Get merchant account details`

---

## 4. Step 3 - Send Enriched Context to LLM

The agent sends complete context to the LLM (for example, Azure AI Foundry), including:
- user prompt
- system prompt (rules and behavior)
- chat history
- available tool list

---

## 5. Step 4 - LLM Intent Analysis and Tool Selection

The LLM analyzes:
- user intent
- available tools

The LLM decides:
- which tool to call
- what parameters to pass

Example decision:
`getAccount(userId, merchantId)`

---

## 6. Step 5 - Tool Invocation via MCP Server

- Agent calls the selected tool through MCP Server.
- Agent includes required parameters in the tool invocation.

---

## 7. Step 6 - External API or Data Source Execution

- MCP Server executes the selected tool.
- MCP Server calls external systems:
    - API
    - database
    - service

Note: This is the execution step where real business data is fetched or updated.

---

## 8. Step 7 - Tool Response Retrieval (Raw Data)

- External system returns raw response (for example, JSON) to MCP Server.
- MCP Server passes that raw data back to the agent.

---

## 9. Step 8 - Send Tool Output to LLM for Post-Processing

- Agent sends tool output back to the LLM.
- Payload includes:
    - raw response data (JSON)
    - original context (user prompt + system prompt)

---

## 10. Step 9 - LLM Response Formatting and Generation

The LLM:
- converts raw data into a human-readable answer
- applies formatting and response style rules

Example formatted output:

```text
Merchant Details:
- Name: ABC Ltd
- Status: Active
```

---

## 11. Step 10 - Deliver Final Response to User

- Agent returns the final LLM response to the user.

---

## 12. Final Flow (Quick View)

1. Initialize and fetch tools
2. User sends request
3. Agent sends context and tools to LLM
4. LLM selects tool and parameters
5. Agent invokes tool via MCP Server
6. MCP Server calls external API or database
7. External system returns raw data to MCP Server, then to Agent
8. Agent sends raw data back to LLM
9. LLM formats final response
10. Agent sends response to user

---

## 13. Key Architectural Insights

- LLM never calls APIs directly.
- Agent is the orchestrator.
- MCP Server exposes tools (functions).
- External systems hold real data.
- LLM is used twice:
    - first for decision making
    - second for response formatting

---

## 14. One-Line Summary

MCP architecture uses an agent to orchestrate user input, LLM reasoning, and MCP tool execution, where MCP Server performs external API calls and the LLM formats the final user response.
