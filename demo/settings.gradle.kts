rootProject.name = "pnyx"

include(":core", ":app", ":skill-openai", ":validation-openai")
project(":core").projectDir = file("modules/core")
project(":app").projectDir = file("modules/app")
project(":skill-openai").projectDir = file("modules/skill-openai")
project(":validation-openai").projectDir = file("modules/validation-openai")
