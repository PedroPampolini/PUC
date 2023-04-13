#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>

struct Filme {
    char _name[100];
    char _original_name[100];
    char _release_date[100];
    int _duration;
    char _gender[100];
    char _original_tongue[100];
    char _situation[100];
    float _budget;
    char** _keywords;
    int _keywords_length;
};

char* final_line = NULL;
void CleanMemoryFinalLine() {
    if (final_line != NULL) {
        free(final_line);
    }
}
char* final_line2 = NULL;
void CleanMemoryFinalLine2() {
    if (final_line != NULL) {
        free(final_line);
    }
}

char** line_parts = NULL;
void CleanMemoryLineParts() {
    if (line_parts != NULL) {
        for (int i = 0; i < 10; i++) {
            free(line_parts[i]);
        }
        free(line_parts);
    }
}

char* ltrim(char *s) {
    while(isspace(*s)){
        s++;
    }
    return s;
}

char* rtrim(char s[]) {
    char* back = s + strlen(s);
    while(isspace(*--back));
    *(back+1) = '\0';
    return s;
}

char* trim(char *s) {
    return rtrim(ltrim(s));
}

char* replaceAll(const char* s, const char* oldW,
                  const char* newW)
{
    char* result;
    int i, cnt = 0;
    int newWlen = strlen(newW);
    int oldWlen = strlen(oldW);
    for (i = 0; s[i] != '\0'; i++) {
        if (strstr(&s[i], oldW) == &s[i]) {
            cnt++;
            i += oldWlen - 1;
        }
    }
    result = (char*)malloc(i + cnt * (newWlen - oldWlen) + 1);
    i = 0;
    while (*s) {
        if (strstr(s, oldW) == s) {
            strcpy(&result[i], newW);
            i += newWlen;
            s += oldWlen;
        }
        else
            result[i++] = *s++;
    }
    result[i] = '\0';
    return result;
}

char** RemoveTagsAndGetWords(char* line) {
    int count_braces = 0, count_words = 0;
    char char_to_string[2];
    char* final_line = malloc(sizeof(char) * 1000);
    char* final_line2 = malloc(sizeof(char) * 1000);
    strcpy(final_line, "");
    char** line_parts = malloc(sizeof(char*) * 10);
    for (int i = 0; i < 10; i++) {
        line_parts[i] = malloc(sizeof(char) * 1000);
    }
    for (int i = 0; i < strlen(line); i++) {
        if (line[i] =='<') {
            count_braces++;
            if(line[i] != '\0') {
                strcpy(final_line, trim(final_line));
                final_line2 = replaceAll(final_line, "&nbsp;", "");
                strcpy(final_line, final_line2);
                strcpy(line_parts[count_words], final_line);
                strcpy(final_line, "");
                count_words++;
            }
        } else if (line[i] =='>') {
            count_braces--;
        }
        if (count_braces == 0 && line[i] != '>') {
            char_to_string[0] = line[i];
            char_to_string[1] = '\0';
            strcpy(final_line, strcat(final_line, char_to_string));
        }
    }
    if (count_words < 1) {
        strcpy(line_parts[0], final_line);
    } else {
        if(strcmp(final_line, "")) {
            strcpy(final_line, trim(final_line));
            final_line2 = replaceAll(final_line, "&nbsp;", "");
            strcpy(final_line, final_line2);
            strcpy(line_parts[count_words], final_line);
            strcpy(final_line, "");
            count_words++;
        }
    }
    atexit(CleanMemoryLineParts);
    atexit(CleanMemoryFinalLine);
    atexit(CleanMemoryFinalLine2);
    return line_parts;
}

int GetDuration(char* line) {
    char* final_line = malloc(sizeof(char) * 10);
    strcpy(final_line, line);
    strcpy(final_line, trim(final_line));
    int total = 0, hours_count = 0;
    char hours[10] = "0";
    char minutes[10] = "0";
    int final_hours = 0, final_minutes = 0;
    for (int i = 0; i < strlen(final_line); i++) {
        if (final_line[i] >= '0' && final_line[i] <= '9') {
            hours[hours_count] = final_line[i];
            hours[hours_count + 1] = '\0';
            minutes[hours_count] = final_line[i];
            minutes[hours_count + 1] = '\0';
            hours_count++;
        } else if (final_line[i] == 'h') {
            hours[hours_count] = '\0';
            final_hours = atoi(hours) * 60;
            minutes[0] = '0';
            minutes[1] = '\0';
            hours_count = 0;
        } else if (final_line[i] == 'm') {
            minutes[hours_count] = '\0';
            final_minutes = atoi(minutes);
        }
    }
    atexit(CleanMemoryFinalLine);
    return final_hours + final_minutes;
}

float StringToFloat(char* line) {
    char number[20] = "0";
    char char_to_string[2];
    float result;
    for (int i = 0; i < strlen(line); i ++) {
        if (line[i] != '$' && line[i] != ',') {
            char_to_string[0] = line[i];
            char_to_string[1] = '\0';
            strcpy(number, strcat(number, char_to_string));
        }
    }
    result = atof(number);
    return result;
}

char* GetName(char* line) {
    char* final_line = malloc(sizeof(char) * 500);
    for (int i = 0; i < strlen(line); i++) {
        if (line[i] != '(') {
            final_line[i] = line[i];
        } else {
            final_line[i] = '\0';
            break;
        }
    }
    atexit(CleanMemoryFinalLine);
    return trim(final_line);
}

char* GetDate(char* line) {
    char* final_line = malloc(sizeof(char) * 50);
    strcpy(final_line, line);
    strcpy(final_line, trim(final_line));
    strcpy(final_line, GetName(final_line));
    atexit(CleanMemoryFinalLine);
    return final_line;
}

void PrintVector(char** arr, int length) {
    printf("[");
    for (int i = 0; i < length; i++) {
        if (arr[i] != NULL) {
            if (i == length - 1) {
                printf("%s", arr[i]);
                break;
            } else {
                printf("%s, ", arr[i]);
            }
        }
    }
    printf("]");
}

char* GetAllWords(char** words) {
    char* final_line = malloc(sizeof(char) * 500);
    char* final_line2 = malloc(sizeof(char) * 500);
    for (int i = 0; i < 10; i++) {
        if (words[i] != NULL) {
            strcpy(final_line, strcat(final_line, words[i]));
        }
    }
    atexit(CleanMemoryFinalLine);
    atexit(CleanMemoryFinalLine2);
    final_line2 = replaceAll(final_line, "0", "");
    return final_line2;
}

struct Filme ReadHTML(char* source) {
    struct Filme filme;
    filme._original_name[0] = '\0';
    char path[200] = "/tmp/filmes/";
    strcat(path, source);
    FILE *file = fopen(path, "r");
    size_t len = 0;
    int read;

    char* final_line = malloc(sizeof(char) * 10000);
    char* final_line2 = malloc(sizeof(char) * 500);
    char** line_parts = malloc(sizeof(char*) * 20);
    for (int i = 0; i < 20; i++) {
        line_parts[i] = malloc(sizeof(char) * 500);

    }
    int count_keywords = 0;

    while((read = getline(&final_line, &len, file)) != -1) {
        if (strstr(final_line, "<title>") != NULL) {
            strcpy(filme._name, GetName(RemoveTagsAndGetWords(trim(final_line))[1]));
        }
        if (strstr(final_line, "Título original") != NULL) {
            strcpy(filme._original_name, RemoveTagsAndGetWords(trim(final_line))[3]);
        }
        if (strstr(final_line, "class=\"release\"") != NULL) {
            getline(&final_line, &len, file);
            strcpy(filme._release_date, GetDate(RemoveTagsAndGetWords(final_line)[0]));
        }
        if (strstr(final_line, "runtime") != NULL) {
            getline(&final_line, &len, file);
            getline(&final_line, &len, file);
            filme._duration = GetDuration(RemoveTagsAndGetWords(final_line)[0]);
        }
        if (strstr(final_line, "genres") != NULL) {
            getline(&final_line, &len, file);
            getline(&final_line, &len, file);
            strcpy(filme._gender, GetAllWords(RemoveTagsAndGetWords(final_line))); //fazer a funçao das palavras
        }
        if (strstr(final_line, "Idioma original") != NULL) {
            strcpy(filme._original_tongue, RemoveTagsAndGetWords(trim(final_line))[5]);
        }
        if (strstr(final_line, "<bdi>Situação</bdi>") != NULL) {
            strcpy(filme._situation, RemoveTagsAndGetWords(trim(final_line))[4]);
        }
        if (strstr(final_line, "Orçamento") != NULL) {
            filme._budget = StringToFloat(RemoveTagsAndGetWords(trim(final_line))[5]);
        }
        if (strstr(final_line, "/keyword") != NULL) {
            strcpy(final_line2, RemoveTagsAndGetWords(trim(final_line))[2]);
            if (strcmp(final_line2, "") != 0) {
                strcpy(line_parts[count_keywords], final_line2);
                count_keywords++;
            }
        }
    }
    filme._keywords_length = count_keywords;
    filme._keywords = line_parts;

    if (strcmp(filme._situation, "Lançdoo") == 0) {
        strcpy(filme._situation, "Lançado");
    }

    fclose(file);
    atexit(CleanMemoryFinalLine);
    atexit((CleanMemoryFinalLine2));
    atexit(CleanMemoryLineParts);
    return filme;
}

void Print(struct Filme filme) {
    if (filme._original_name[0] == '\0' && filme._budget == 0.0f) {
        printf("%s %s %s %d %s %s %s %d ", filme._name, filme._name, filme._release_date, filme._duration,
               filme._gender, filme._original_tongue, filme._situation, (int)filme._budget);
        PrintVector(filme._keywords, filme._keywords_length);
        printf("\n");
    } else if (filme._original_name[0] == '\0'){
        printf("%s %s %s %d %s %s %s %.1e ", filme._name, filme._name, filme._release_date, filme._duration,
               filme._gender, filme._original_tongue, filme._situation, filme._budget);
        PrintVector(filme._keywords, filme._keywords_length);
        printf("\n");
    } else if (filme._budget == 0.0f) {
        printf("%s %s %s %d %s %s %s %d ", filme._name, filme._original_name, filme._release_date, filme._duration,
               filme._gender, filme._original_tongue, filme._situation, (int)filme._budget);
        PrintVector(filme._keywords, filme._keywords_length);
        printf("\n");
    }
    else {
        printf("%s %s %s %d %s %s %s %.1e ", filme._name, filme._original_name, filme._release_date, filme._duration,
               filme._gender, filme._original_tongue, filme._situation, filme._budget);
        PrintVector(filme._keywords, filme._keywords_length);
        printf("\n");
    }
}

int main() {
    char movie[50];
    struct Filme filme;
    scanf("%[^\n]%*c", movie);
    while (strcmp(movie, "FIM") != 0) {
        filme = ReadHTML(movie);
        Print(filme);
        scanf("%[^\n]%*c", movie);
    }
    return 0;
}
