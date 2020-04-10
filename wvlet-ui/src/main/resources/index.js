/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import * as monaco from 'monaco-editor'
// or import * as monaco from 'monaco-editor/esm/vs/editor/editor.api';
// if shipping only a subset of the features & languages is desired
//
// monaco.editor.create(document.getElementById('monaco-editor'), {
//     value: 'select * from table',
//     language: 'sql',
//     theme: "vs-dark",
// });

function createEditor(id, value, lang) {
    monaco.editor.create(document.getElementById(id), {
        value: value,
        language: lang,
        theme: 'vs-dark',
    });
}

