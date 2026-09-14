import './Greeting.css';

import type {AnimationEvent} from 'react';
import {useEffect, useState} from 'react';
import {JSLogo} from '../JSLogo/JSLogo.tsx';
import {Greeting2 as KotlinGreeting, User as KotlinUser} from 'webApp-shared';

export function Greeting() {
    const greeting = new KotlinGreeting();
    const [isVisible, setIsVisible] = useState<boolean>(false);
    const [isAnimating, setIsAnimating] = useState<boolean>(false);
    const [user, setUser] = useState<KotlinUser | null>(null);

    const handleClick = () => {
        if (isVisible) {
            setIsAnimating(true);
        } else {
            setIsVisible(true);
        }
    };

    const handleAnimationEnd = (event: AnimationEvent<HTMLDivElement>) => {
        if (event.animationName === 'fadeOut') {
            setIsVisible(false);
            setIsAnimating(false);
        }
    };

    useEffect(() => {
        getUser()
            .then(setUser)
            .catch(console.error)
    }, [])

    return (
        <div className="greeting-container">
            <button onClick={handleClick} className="greeting-button">
                Click me!
            </button>

            {isVisible && user && (
                <div className={isAnimating ? 'greeting-content fade-out' : 'greeting-content'}
                     onAnimationEnd={handleAnimationEnd}>
                    <JSLogo/>
                    <div>{user.name} {user.lastname}: {greeting.greet()}</div>
                </div>
            )}

        </div>
    );
}

async function getUser(): Promise<KotlinUser> {
    const response = await fetch('http://localhost:8080/api/user', {
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
    })

    if (!response.ok) {
        throw new Error(`HTTP ${response.status}`)
    }

    return response.json();
}
