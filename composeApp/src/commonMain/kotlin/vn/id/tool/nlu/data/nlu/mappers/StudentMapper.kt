package vn.id.tool.nlu.data.nlu.mappers

import vn.id.tool.nlu.data.nlu.dto.StudentDto
import vn.id.tool.nlu.domain.Student

fun StudentDto.toStudent(): Student {
    return Student(
        id = id,
        name = name,
        email = email,
        username = userName
    )
}