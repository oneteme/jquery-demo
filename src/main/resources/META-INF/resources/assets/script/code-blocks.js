const codeLangMap = {
    java: { label: 'Java', icon: 'ti-coffee', color: '#f89820' },
    sql: { label: 'SQL', icon: 'ti-database', color: '#00d9ff' },
    json: { label: 'JSON', icon: 'ti-braces', color: '#94a3b8' },
    bash: { label: 'Shell', icon: 'ti-terminal-2', color: '#4eaa25' },
    javascript: { label: 'URL', icon: 'ti-world', color: '#00d9ff' },
    js: { label: 'URL', icon: 'ti-world', color: '#00d9ff' },
    scss: { label: 'URL', icon: 'ti-world', color: '#00d9ff' },
    php: { label: 'URL', icon: 'ti-world', color: '#00d9ff' }
};

export function initCodeBlocks(root = null) {
    const $root = $(root || document);
    const container = $root.get(0);

    // Step 1: tag every <pre> that's preceded by a playdata comment
    const walker = document.createTreeWalker(container, NodeFilter.SHOW_COMMENT, null);
    let node;
    while ((node = walker.nextNode())) {
        const match = node.textContent.match(/^\s*playdata:\s*(\{[\s\S]*\})\s*$/);
        if (!match) continue;

        let sibling = node.nextSibling;
        while (sibling && sibling.nodeType === Node.TEXT_NODE && !sibling.textContent.trim()) {
            sibling = sibling.nextSibling;
        }
        if (sibling && sibling.tagName === 'PRE') {
            sibling.setAttribute('data-play', match[1]);
        }
    }

    // Step 2: wrap every code block with a header
    $root.find('pre > code[class*="language-"]').each(function () {
        const $code = $(this);
        const $pre = $code.parent();
        if ($pre.parent().hasClass('code-block-wrapper')) return;

        const langMatch = ($code.attr('class') || '').match(/language-(\S+)/);
        const lang = langMatch ? langMatch[1] : 'text';
        const info = codeLangMap[lang] || { label: lang.toUpperCase(), icon: 'ti-code', color: 'var(--jarvis-cyan)' };
        const playData = $pre.attr('data-play');

        const $wrapper = $('<div class="code-block-wrapper"></div>');
        const $header = $('<div class="code-block-header"></div>');

        $header.append(
            $('<div class="code-block-lang"><i class="ti ' + info.icon + '"></i><span>' + info.label + '</span></div>')
                .css('color', info.color)
        );

        const $actions = $('<div class="code-block-actions"></div>');

        $actions.append(
            '<button type="button" class="code-block-copy-btn" title="Copy code"><i class="ti ti-copy"></i></button>'
        );

        if (playData) {
            $actions.append(
                $('<button type="button" class="code-block-play-btn btn-trigger" title="Run this example" show=".content.jquery-display,.form-sidebar-actions" hide=".content" visible=".show-docs,.show-info"><i class="ti ti-player-play"></i></button>')
                    .attr('data-play', playData)
            );
        }

        $header.append($actions);
        $pre.before($wrapper);
        $wrapper.append($header).append($pre);
    });
}

$(document).on('click', '.code-block-copy-btn', function () {
    const $btn = $(this);
    const code = $btn.closest('.code-block-wrapper').find('code').text();

    navigator.clipboard.writeText(code).then(function () {
        $btn.addClass('copied');
        $btn.find('i').removeClass('ti-copy').addClass('ti-check');
        setTimeout(function () {
            $btn.removeClass('copied');
            $btn.find('i').removeClass('ti-check').addClass('ti-copy');
        }, 1200);
    });
});